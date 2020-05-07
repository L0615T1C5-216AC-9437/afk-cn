package afk;

import arc.Events;
import arc.util.CommandHandler;
import arc.util.Log;
import arc.util.Strings;
import mindustry.entities.type.Player;
import mindustry.game.EventType;
import mindustry.plugin.Plugin;
import org.json.JSONObject;

import java.util.HashMap;

public class Main extends Plugin {
    //Var
    public static JSONObject data = new JSONObject();
    public static Thread afk;
    public static HashMap<String, pd> playerList = new HashMap<>();
    Boolean enabled = false;

    ///Var
    //on start
    public Main() {
        byteCode.assertCore("afk");
        data = byteCode.get("afk");
        if (data == null) {
            Log.err("Invalid file - " + System.getProperty("user.home") + "/mind_db/afk.cn");
            Log.info("Reset file using command `afk-clear`");
            return;
        }
        enabled = true;
        cycle a = new cycle(Thread.currentThread());
        a.setDaemon(false);
        a.start();

        Events.on(EventType.WorldLoadEvent.class, event -> {
            if (enabled) {
                if (!afk.isAlive()) {
                    cycle b = new cycle(Thread.currentThread());
                    b.setDaemon(false);
                    b.start();
                }
            }
        });
        Events.on(EventType.PlayerJoin.class, event -> {
            Player player = event.player;

            if (enabled) playerList.put(player.uuid, new pd());
        });
        Events.on(EventType.PlayerLeave.class, event -> {
            Player player = event.player;

            if (enabled) playerList.remove(player.uuid);
        });
        Events.on(EventType.BlockBuildEndEvent.class, event -> {
            Player player = event.player;

            if (enabled) {
                if (!playerList.containsKey(player.uuid)) playerList.put(player.uuid, new pd());
                playerList.get(player.uuid).addBb();
            }
        });
        Events.on(EventType.PlayerChatEvent.class, event -> {
            Player player = event.player;

            if (!event.message.startsWith("/") && enabled) {
                if (!playerList.containsKey(player.uuid)) playerList.put(player.uuid, new pd());
                playerList.get(player.uuid).addMs();
            }
        });
    }

    public void registerServerCommands(CommandHandler handler) {
        handler.register("afk-clear", "generates the default afk.cn file", arg -> {
            data = new JSONObject();
            data.put("messagesPM", 3);
            data.put("bbPM", 15);
            data.put("maxAfk", 5);
            byteCode.make("afk", data);
            byteCode.save("afk", data);
            Log.info("afk.cn cleared successfully.");
        });
    }
}