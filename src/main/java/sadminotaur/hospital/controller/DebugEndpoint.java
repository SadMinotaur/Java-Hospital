package sadminotaur.hospital.controller;

import sadminotaur.hospital.daoimpl.CommonDaoImpl;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/debug")
public class DebugEndpoint {

    @Autowired
    private CommonDaoImpl commonDao;

    @PostMapping(path = "/clear", produces = MediaType.APPLICATION_JSON_VALUE)
    public void clear() throws ServiceException {
        commonDao.clear();
    }
}
