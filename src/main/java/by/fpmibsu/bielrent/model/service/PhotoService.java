package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.model.dao.PhotoDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.req.PhotoReq;
import by.fpmibsu.bielrent.model.dtovalidator.PhotoValidator;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationException;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationResult;
import by.fpmibsu.bielrent.model.entity.Photo;
import by.fpmibsu.bielrent.model.dtomapper.PhotoMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoService {
    private static final ImageService imageService = ImageService.getInstance();
    private static final PhotoMapper photoMapper = PhotoMapper.getInstance();
    private static final PhotoValidator photoValidator = PhotoValidator.getInstance();
    private static final PhotoDaoImpl photoDao = PhotoDaoImpl.getInstance();
    private static final ConnectionPoolImpl connPool = ConnectionPoolImpl.getInstance();


    private static final PhotoService INSTANCE = new PhotoService();
    public static PhotoService getInstance(){
        return INSTANCE;
    }
    Logger logger = LogManager.getLogger(PhotoService.class);

    public long insertIfValid(PhotoReq photoReq, Long listingId) throws DaoException, ValidationException, IOException{
        try (var conn = connPool.getConnection()) {
            return insertIfValid(photoReq, listingId, conn);
        } catch (SQLException e) {
            logger.error("insert if valid photo service error");
            throw new DaoException(e);
        }
    }

    public long insertIfValid(PhotoReq photoReq, Long listingId, Connection conn) throws DaoException, ValidationException, IOException {
        ValidationResult vr = photoValidator.validate(photoReq);
        if (!vr.isValid()) {
            throw new ValidationException(vr.getErrors());
        }

        Photo photoEntity = photoMapper.toEntity(photoReq, listingId);

        imageService.uploadImage(photoEntity.getPath(), photoReq.getPhoto().getInputStream());
        return photoDao.insert(photoEntity, conn);
    }
}
