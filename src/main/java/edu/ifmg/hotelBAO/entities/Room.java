package edu.ifmg.hotelBAO.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ifmg.hotelBAO.dtos.RoomDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal price;
    private String imageUrl;

    @OneToMany(mappedBy = "room")
    @JsonIgnore // evita loop ao serializar o RoomDTO
    // Se nao ignorar o campo stays, e Stay tiver Cient e Room como propriedades (como é o caso),
    // vai entrar em loop infinito ao serializar Client -> Stay -> Client -> Stay...
    private List<Stay> stays = new ArrayList<>();

    public Room() {

    }

    public Room(Long id, String description, BigDecimal price, String imageUrl) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Room(RoomDTO dto) {
        this.id = dto.getId();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
        this.imageUrl = dto.getImageUrl();
    }

    public Room(Room entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imageUrl = entity.getImageUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Room room)) return false;
        return Objects.equals(id, room.id) && Objects.equals(description, room.description) && Objects.equals(price, room.price) && Objects.equals(imageUrl, room.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, imageUrl);
    }
}
