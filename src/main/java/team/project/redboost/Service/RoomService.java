package team.project.redboost.Service;


import team.project.redboost.Models.Room;
import java.util.List;


public interface RoomService {
    List<Room> getAllRooms();
    Room getRoomById(Long id);
    Room createRoom(Room room);
    Room updateRoom(Long id, Room room);
    void deleteRoom(Long id);
}

