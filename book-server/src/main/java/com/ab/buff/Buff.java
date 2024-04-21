package com.ab.buff;

import com.ab.websocket.game.dto.GameMessageDTO;
import com.ab.websocket.game.Player;

public interface Buff {
    Buff next(Buff buff);
    Player Handler(GameMessageDTO gameMessageDTO,Player player);
}
