package com.coderz.app;

import java.util.*;
import com.beust.jcommander.Parameter;

public class Args{

    @Parameter
    public List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-mode" }, description = "Type of execution mapper or reducer")
    public String mode = "mapper";

    @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
    public String groups;

    @Parameter(names = "-debug", description = "Debug mode")
    public boolean debug = false;

}
