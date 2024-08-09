package br.edu.utfpr.sistemarquivos;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

public enum Command {

    LIST() {
        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("LIST") || commands[0].startsWith("list");
        }

        @Override
        Path execute(Path path) throws IOException {

            try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
                System.out.println("Content of " + path.toUri());
                directory.forEach(p -> System.out.println(p.getFileName()));
            }

            return path;
        }
    },
    SHOW() {
        private String[] parameters = new String[]{};

        @Override
        void setParameters(String[] parameters) {
            this.parameters = parameters;
        }

        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("SHOW") || commands[0].startsWith("show");
        }

        @Override
        Path execute(Path path) throws IOException {
            final var newPath = Path.of(path + File.separator + Arrays.stream(parameters).toList().get(1));
            final String[] extension = Arrays.toString(parameters).split("\\.", 0);

            if (!Files.isDirectory(newPath)) {
                if(Arrays.toString(extension).contains("txt")) {
                    new FileReader().read(newPath);
                } else {
                    System.out.println("Extension not supported");
                }
            } else {
                System.out.println("This command should be used with files only");
            }

            return path;
        }
    },
    BACK() {
        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("BACK") || commands[0].startsWith("back");
        }

        @Override
        Path execute(Path path) {

                int lastIndex = path.toString().lastIndexOf("\\");
                Path newPath = Path.of(path.toString().substring(0, lastIndex));

                if(newPath.endsWith("hd")){
                    path = newPath;
                } else {
                    System.out.println("Cannot navigate beyond the root directory");
                }

            return path;
        }
    },
    OPEN() {
        private String[] parameters = new String[]{};

        @Override
        void setParameters(String[] parameters) {
            this.parameters = parameters;
        }

        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("OPEN") || commands[0].startsWith("open");
        }

        @Override
        Path execute(Path path) {

            if (Arrays.stream(parameters).toList().size() > 1){
                path = Path.of(path + File.separator + Arrays.stream(parameters).toList().get(1));
            } else {
                System.out.println("Invalid command, please enter a parameter");
            }

            return path;
        }
    },
    DETAIL() {
        private String[] parameters = new String[]{};

        @Override
        void setParameters(String[] parameters) {
            this.parameters = parameters;
        }

        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("DETAIL") || commands[0].startsWith("detail");
        }

        @Override
        Path execute(Path path) throws IOException {
            ;
            final var newPath = Path.of(path + File.separator + Arrays.stream(parameters).toList().get(1));
            System.out.println("Content of " + newPath.toUri());
            File f = new File(String.valueOf(newPath));
            final var info = Files.readAttributes(newPath, BasicFileAttributes.class);

            System.out.println("Is Directory " + "[" + f.isDirectory() + "]");
            System.out.println("Size " + "[" + f.length() + "]");
            System.out.println("Created on " + "[" + info.creationTime() + "]");
            System.out.println("Last access time " + "[" + info.lastAccessTime() + "]");

            return path;
        }
    },
    EXIT() {
        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("EXIT") || commands[0].startsWith("exit");
        }

        @Override
        Path execute(Path path) {
            System.out.print("Saindo...");
            return path;
        }

        @Override
        boolean shouldStop() {
            return true;
        }
    };

    abstract Path execute(Path path) throws IOException;

    abstract boolean accept(String command);

    void setParameters(String[] parameters) {
    }

    boolean shouldStop() {
        return false;
    }

    public static Command parseCommand(String commandToParse) {

        if (commandToParse.isBlank()) {
            throw new UnsupportedOperationException("Type something...");
        }

        final var possibleCommands = values();

        for (Command possibleCommand : possibleCommands) {
            if (possibleCommand.accept(commandToParse)) {
                possibleCommand.setParameters(commandToParse.split(" "));
                return possibleCommand;
            }
        }

        throw new UnsupportedOperationException("Can't parse command [%s]".formatted(commandToParse));
    }
}
