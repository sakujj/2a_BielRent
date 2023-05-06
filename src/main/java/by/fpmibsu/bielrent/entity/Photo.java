package by.fpmibsu.bielrent.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Photo implements Entity{
    private Long id;
    private Long listingId;

    private String path;
}
