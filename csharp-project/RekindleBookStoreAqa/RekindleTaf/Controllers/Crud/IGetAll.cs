using System.Collections.Generic;
using RekindleTaf.Model;

namespace RekindleTaf.Controllers.Crud;

public interface IGetAll<T, U> where T : IValueObject
{
    U GetAll(string param = "");
    List<T> GetAllSuccessfully(string param = "");
}