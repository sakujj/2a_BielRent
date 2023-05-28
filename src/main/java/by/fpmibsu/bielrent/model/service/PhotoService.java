package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.dao.PhotoDao;
import by.fpmibsu.bielrent.model.dao.PhotoDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.InsertPhotoDto;
import by.fpmibsu.bielrent.model.dto.validator.PhotoValidator;
import by.fpmibsu.bielrent.model.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.model.entity.Photo;
import by.fpmibsu.bielrent.model.dtomapper.toentity.InsertPhotoMapperToEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoService {
    private static final ImageService imageService = ImageService.getInstance();
    private static final InsertPhotoMapperToEntity photoMapperToEntity = InsertPhotoMapperToEntity.getInstance();
    private static final PhotoValidator photoValidator = PhotoValidator.getInstance();
    private static final PhotoDao photoDao = PhotoDaoImpl.getInstance();

    private static final PhotoService INSTANCE = new PhotoService();
    public static PhotoService getInstance(){
        return INSTANCE;
    }

    @SneakyThrows
    public long insertIfValid(InsertPhotoDto insertPhotoDto) throws DaoException {
        long id = -1;

        ValidationResult vr = photoValidator.validate(insertPhotoDto);
        if (!vr.isValid()) {
            return id;
        }
        Photo photoEntity = photoMapperToEntity.mapFrom(insertPhotoDto);
        imageService.upload(photoEntity.getPath(), insertPhotoDto.getPhoto().getInputStream());
        id = photoDao.insert(photoEntity);

        return id;
    }
}
