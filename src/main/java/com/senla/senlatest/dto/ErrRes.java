package com.senla.senlatest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErrRes (String errmsg, int errcode, LocalDateTime timestamp, String path)
{
}
