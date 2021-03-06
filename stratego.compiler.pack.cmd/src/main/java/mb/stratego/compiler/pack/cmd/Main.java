package mb.stratego.compiler.pack.cmd;

import java.nio.file.Path;

import org.metaborg.util.log.ILogger;
import org.metaborg.util.log.LoggerUtils;

import mb.stratego.compiler.pack.cmd.arguments.Arguments;
import mb.stratego.compiler.pack.cmd.arguments.Boilerplate;
import mb.stratego.compiler.pack.cmd.arguments.SharedArguments;
import mb.stratego.compiler.pack.cmd.arguments.SingleStrategy;
import mb.stratego.compiler.pack.Packer;

public class Main {
    private static final ILogger logger = LoggerUtils.logger(Main.class);

    public static void main(String[] args) {
        final Arguments arguments = Arguments.parse(args);

        try {
            SharedArguments commandArgs = arguments.getParsedCommandArguments();
            if(commandArgs instanceof SingleStrategy) {
                SingleStrategy ssArgs = (SingleStrategy) commandArgs;
                final Path inputDir = ssArgs.inputDir.toAbsolutePath();
                final String strategyName;
    
                if(ssArgs.strategyName != null) {
                    strategyName = ssArgs.strategyName;
                } else {
                    strategyName = inputDir.getName(inputDir.getNameCount() - 1).toString();
                }
    
                Packer.packStrategy(inputDir, commandArgs.outputFile, strategyName);
            } else if(commandArgs instanceof Boilerplate) {
                Boilerplate bArgs = (Boilerplate) commandArgs;
                final Path inputDir = bArgs.inputDir.toAbsolutePath();

                Packer.packBoilerplate(inputDir, commandArgs.outputFile);
            } else {
                throw new IllegalArgumentException("Unknown command parsed");
            }
        } catch(Exception e) {
            logger.error("Error during packing of strategy", e);
            System.exit(1);
        }
    }
}
