package afk;

import arc.util.Log;
import mindustry.Vars;
import mindustry.entities.type.Player;
import mindustry.gen.Call;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import static mindustry.Vars.netServer;
import static mindustry.Vars.playerGroup;

public class cycle extends Thread {
    private Thread MainT;
    private JSONObject adata;

    public cycle(Thread main) {
        MainT = main;
    }

    public void run() {
        Log.info("async started - Waiting 60 Seconds");
        Main.afk = Thread.currentThread();
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (Exception e) {
        }
        Log.info("async running");
        while (MainT.isAlive()) {
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (Exception e) {
            }

            byteCode.assertCore("afk");
            adata = byteCode.get("afk");

            if (!playerGroup.isEmpty()) {
                for (Player p : Vars.playerGroup.all()) {
                    if (Main.playerList.containsKey(p.uuid)) {
                        pd data = Main.playerList.get(p.uuid);
                        if (data.getBb() > adata.getInt("bbPM")) continue;
                        if (data.getMs() > adata.getInt("messagesPM")) continue;
                        if (data.getX()+5 < p.x && p.x < data.getX()-5 && data.getY()+5 < p.y && p.y < data.getY()-5) continue;

                        Main.playerList.get(p.uuid).addAfk();
                    }
                }
                for (Player p : Vars.playerGroup.all()) {
                    if (Main.playerList.containsKey(p.uuid)) {
                        if (Main.playerList.get(p.uuid).getAfk() > adata.getInt("maxAfk")) Call.onKick(p.con, "Reason: Inactivity");
                    }
                }
            }

        }
    }
}
