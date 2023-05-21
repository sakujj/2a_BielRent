package by.fpmibsu.bielrent.model.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class Photo implements Entity{
    private Long id;
    private Long listingId;

    private String path;
}
