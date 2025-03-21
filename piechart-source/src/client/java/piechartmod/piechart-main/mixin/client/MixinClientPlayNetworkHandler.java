package buildbattlemod.buildbattle.mixin.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
	private static final File SAVE_FILE = new File("legendary_rains.json");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static int legendaryCount = loadLegendaryCount();

	@Inject(at = @At("HEAD"), method = "onTitle")

	private void onTitle(TitleS2CPacket packet, CallbackInfo info) {

		// TitleS2CPacket
		Text title = Text.of(packet.text().getString());
		if (title != null && MinecraftClient.getInstance().player != null) {
			String cleanTitle = title.getString();
			System.out.println("Received title: " + cleanTitle);

			if ("LEGENDARY".equalsIgnoreCase(cleanTitle)) {
				System.out.println("Sending legendary title");
				MinecraftClient.getInstance().player.sendMessage(
						Text.literal("LEGENDARY")
								.styled(style->
										style.withBold(true)
												.withColor(0xFFAA00))
								.append(
										Text.literal("You were given a legendary rain! You now have " + legendaryCount + " Legendary Rain.")
												.formatted(Formatting.AQUA)
								)

						,
						false
				);
				legendaryCount++;
				saveLegendaryCount();
				System.out.println("Legendary count updated: " + legendaryCount);
			}
		}
	}

	@Unique
	private static int loadLegendaryCount() {
		if (!SAVE_FILE.exists()) {
			return 0;
		}
		try (FileReader reader = new FileReader(SAVE_FILE)) {
			JsonObject json = GSON.fromJson(reader, JsonObject.class);
			return json.has("legendaryCount") ? json.get("legendaryCount").getAsInt() : 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Unique
	private static void saveLegendaryCount() {
		JsonObject json = new JsonObject();
		json.addProperty("legendaryCount", legendaryCount);
		try (FileWriter writer = new FileWriter(SAVE_FILE)) {
			GSON.toJson(json, writer);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
