package com.ahmed.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ahmed.myapp.web.rest.TestUtil;

public class TerminalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Terminal.class);
        Terminal terminal1 = new Terminal();
        terminal1.setId(1L);
        Terminal terminal2 = new Terminal();
        terminal2.setId(terminal1.getId());
        assertThat(terminal1).isEqualTo(terminal2);
        terminal2.setId(2L);
        assertThat(terminal1).isNotEqualTo(terminal2);
        terminal1.setId(null);
        assertThat(terminal1).isNotEqualTo(terminal2);
    }
}
