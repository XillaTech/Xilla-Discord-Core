package net.xilla.discord.manager.embed;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.entities.EntityBuilder;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.StoredData;
import net.xilla.discord.DiscordCore;
import net.xilla.discord.api.DiscordAPI;
import net.xilla.discord.api.embed.EmbedFormat;
import net.xilla.discord.api.placeholder.Placeholder;
import net.xilla.discord.manager.placeholder.object.HelpPlaceholder;
import org.json.simple.JSONObject;

public class DiscordEmbed extends ManagerObject implements EmbedFormat {

    @Getter
    private EmbedBuilder embedBuilder;

    public DiscordEmbed(String name, EmbedBuilder embedBuilder) {
        super(name, "DiscordEmbed");
        this.embedBuilder = embedBuilder;
    }

    public DiscordEmbed() {}

    public MessageEmbed build() {
        return embedBuilder.build();
    }

    public MessageEmbed buildTemplate(Member member) {
        EmbedBuilder builder = new EmbedBuilder(this.embedBuilder);

        Placeholder placeholder = DiscordCore.getInstance().getPlaceholderProcessor().get("Help");
        placeholder.injectSystem(builder);

        return builder.build();
    }

    @Override
    public void loadSerializedData(XillaJson json) {
        super.loadSerializedData(json);

        JSONObject data = json.get("data");

        DataObject dataObject = DataObject.fromJson(data.toJSONString());
        dataObject.put("type", EmbedType.RICH.name());
        EntityBuilder entityBuilder = new EntityBuilder(DiscordCore.getInstance().getJda());

        this.embedBuilder = new EmbedBuilder(entityBuilder.createMessageEmbed(dataObject));
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = super.getSerializedData();

        XillaJson data = new XillaJson().parse(new String(embedBuilder.build().toData().toJson()));
        json.put("data", data.getJson());

        return json;
    }

    @Override
    public EmbedBuilder getBuilder() {
        return embedBuilder;
    }

    @Override
    public void setBuilder(EmbedBuilder builder) {
        this.embedBuilder = builder;
    }

}