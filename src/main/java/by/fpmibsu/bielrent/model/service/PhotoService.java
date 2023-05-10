package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.dao.PhotoDao;
import by.fpmibsu.bielrent.model.dao.PhotoDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.controller.dto.PhotoDto;
import by.fpmibsu.bielrent.controller.dto.validator.PhotoValidator;
import by.fpmibsu.bielrent.controller.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.model.entity.Photo;
import by.fpmibsu.bielrent.model.mapper.toentity.PhotoMapperToEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

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
