package backend.repository;

import backend.domain.Channel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
public class ChannelRepositoryTest {

    @Autowired
    private ChannelRepository channelRepository;


    @Test
    public void testFindById() {
        Optional<Channel> channel = channelRepository.findById(1L);

        assertTrue(channel.isPresent());
        assertEquals("EMAIL",channel.get().getType().toString());
    }

    @Test
    public void testFindAll() {
        List<Channel> channels = channelRepository.findAll();

        assertFalse(channels.isEmpty());
        assertEquals(3, channels.size());
        assertEquals("EMAIL", channels.get(0).getType().toString());
        assertEquals("SMS", channels.get(1).getType().toString());
        assertEquals("PUSH_NOTIFICATION", channels.get(2).getType().toString());
    }
}
