package team.project.redboost.Service;



import team.project.redboost.Models.Room;
import team.project.redboost.Repository.RoomRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room non trouvée"));
    }

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long id, Room updatedRoom) {
        Room existingRoom = getRoomById(id);
        existingRoom.setRoomId(updatedRoom.getRoomId());
        existingRoom.setLink(updatedRoom.getLink());
        existingRoom.setActive(updatedRoom.isActive());
        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}


