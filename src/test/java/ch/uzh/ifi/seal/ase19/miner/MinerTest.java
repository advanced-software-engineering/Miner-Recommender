package ch.uzh.ifi.seal.ase19.miner;

import com.google.common.io.Files;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MinerTest {
    @Test
    public void emptyArgs() {
        String args[] = new String[]{};

        assertDoesNotThrow(() -> Miner.main(args));
    }

    @Test
    public void run() {
        String contextDirectory = Files.createTempDir().getAbsolutePath();
        String modelDirectory = Files.createTempDir().getAbsolutePath();

        String args[] = new String[]{contextDirectory, modelDirectory};
        assertDoesNotThrow(() -> Miner.main(args));
    }
}