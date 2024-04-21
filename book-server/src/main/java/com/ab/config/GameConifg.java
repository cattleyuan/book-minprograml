package com.ab.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author cattleYuan
 * @date 2024/2/24
 */
@Configuration
@ConfigurationProperties(prefix = "sky.game")
@Data
public class GameConifg {

    private Integer answerTime;

    private Integer clueNumber;

    private Integer puzzleIntegral;

    private Integer gameStage;
}
