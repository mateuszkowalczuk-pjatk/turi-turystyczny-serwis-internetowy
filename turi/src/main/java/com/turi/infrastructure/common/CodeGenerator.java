package com.turi.infrastructure.common;

import java.util.Random;

public final class CodeGenerator
{
    public static Integer generateCode()
    {
        return 100000 + new Random().nextInt(900000);
    }
}
