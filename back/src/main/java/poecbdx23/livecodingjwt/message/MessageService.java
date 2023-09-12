package poecbdx23.livecodingjwt.message;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import poecbdx23.livecodingjwt.message.Message;
import poecbdx23.livecodingjwt.message.MessageRepository;
import poecbdx23.livecodingjwt.user.User;
import poecbdx23.livecodingjwt.user.UserRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public List<Message> getAllMessages() {

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String role = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        if (role.equals("ROLE_USER")) {
            return messageRepository.findByUserRole("ROLE_ADMIN");
        } else {
            return messageRepository.findByUserRole("ROLE_USER");
        }
    }


    public Message addMessage(Message message) {

        User user = getCurrentUser();
        message.setUser(user);
        message.setTimestamp(new Date());

        return messageRepository.save(message);

    }

//    public Message addMessage(Message message, Long receiverId) {
//        Optional<User> receiverOptional = userRepository.findById(receiverId);
//
//        if (receiverOptional.isPresent()) {
//            User receiver = receiverOptional.get();
//            message.setReceiver(receiver);
//            message.setUser(getCurrentUser());
//            message.setTimestamp(new Date());
//            return messageRepository.save(message);
//        } else {
//            return null;
//        }
//    }


    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Current user not found");
        }
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "not found"));
    }

    public Message updateMessage(Message message, Long id) {

        Message foundMessage = getMessageById(id);
        foundMessage.setResume(message.getResume());

        return messageRepository.save(foundMessage);
    }

    public void deleteMessage(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message user not found"));

        messageRepository.deleteById(id);
    }

}
