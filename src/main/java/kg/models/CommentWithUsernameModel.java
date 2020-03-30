package kg.models;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentWithUsernameModel {
    String username;
    String text;
}
