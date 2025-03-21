package buildbattlemod.buildbattle;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.text.Style;
import net.minecraft.text.MutableText;
import com.mojang.serialization.DataResult;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BuildBattleClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
}
