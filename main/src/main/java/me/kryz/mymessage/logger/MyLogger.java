package me.kryz.mymessage.logger;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MyLogger {
    @Getter
    private static final Logger Logger = LoggerFactory.getLogger("MyMessage");
}
