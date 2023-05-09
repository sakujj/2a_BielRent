package by.fpmibsu.bielrent.service;

import by.fpmibsu.bielrent.dao.PhotoDao;
import by.fpmibsu.bielrent.dao.PhotoDaoImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.PhotoDto;
import by.fpmibsu.bielrent.dto.validator.PhotoValidator;
import by.fpmibsu.bielrent.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.entity.Photo;
import by.fpmibsu.bielrent.mapper.toentity.PhotoMapperToEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoService {
    private static final ImageService imageService = ImageService.getInstance();
    private static final PhotoMapperToEntity photoMapperToEntity = PhotoMapperToEntity.getInstance();
    private static final PhotoValidator photoValidator = PhotoValidator.getInstance();
    private static final PhotoDao photoDao = PhotoDaoImpl.getInstance();

    private static final PhotoService INSTANCE = new PhotoService();
    public static PhotoService getInstance(){
        return INSTANCE;
    }

    @SneakyThrows
    public long insert(PhotoDto photoDto) throws DaoException {
        long id = -1;

        ValidationResult vr = photoValidator.validate(photoDto);
        if (!vr.isValid()) {
            return id;
        }
        Photo photoEntity = photoMapperToEntity.mapFrom(photoDto);
        imageService.upload(photoEntity.getPath(), photoDto.getPhoto().getInputStream());
        id = photoDao.insert(photoEntity);

        return id;
    }
}
