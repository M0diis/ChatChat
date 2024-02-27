package at.helpch.chatchat.hooks.towny;

import at.helpch.chatchat.api.holder.FormatsHolder;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.ResidentList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class TownyNationChannel extends AbstractTownyChannel {

    public TownyNationChannel(@NotNull final String name,
                              @NotNull final String messagePrefix,
                              @NotNull final List<String> toggleCommands,
                              @NotNull final String channelPrefix,
                              @NotNull final FormatsHolder formats,
                              @NotNull final List<String> worlds,
                              final int radius) {
        super(name, messagePrefix, toggleCommands, channelPrefix, formats, worlds, radius);
    }

    @Override
    protected @Nullable ResidentList residentList(@NotNull final Resident resident) {
        return resident.getNationOrNull();
    }
}
