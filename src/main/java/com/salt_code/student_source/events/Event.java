package com.salt_code.student_source.events;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
public abstract class Event {

    private static int count = 0;
    private final int orderId = ++count;

    private final UUID streamId;
    @Setter private Instant createdAt = Instant.now();

    public Event(UUID streamId) {
        this.streamId = streamId;
    }
}
