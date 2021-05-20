package net.xilla.discord.reflection;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.reflection.storage.StorageReflection;
import net.xilla.discord.DiscordCore;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;

public class MemberReflection extends StorageReflection<Member> {

    public MemberReflection() {
        super(Member.class);
    }

    @Override
    public Member loadFromSerializedData(ConfigFile configFile, Object o, Field field, Object o1) {
        JSONObject json = (JSONObject) o1;

        String guildID = json.get("guildID").toString();
        String userID = json.get("userID").toString();

        Guild guild = DiscordCore.getInstance().getJda().getGuildById(guildID);

        if(guild == null) {
            return null;
        }

        return guild.getMemberById(userID);
    }

    @Override
    public Object getSerializedData(ConfigFile configFile, Object o, Field field, Member member) {
        JSONObject json = new JSONObject();

        json.put("guildID", member.getGuild().getId());
        json.put("userID", member.getGuild().getId());

        return json;
    }

}
