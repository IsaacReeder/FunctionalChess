package com.coderz.app;

import java.util.*;
import com.beust.jcommander.Parameter;

public class Args{

    @Parameter
    public List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-mode" }, description = "Type of execution play")
    public String mode = "play";

    @Parameter(names = "-debug", description = "Debug mode")
    public boolean debug = false;

    @Parameter(names = "-depth", description = "depth of the game tree")
    public int depth = 3;
    public boolean debug = false;
}
