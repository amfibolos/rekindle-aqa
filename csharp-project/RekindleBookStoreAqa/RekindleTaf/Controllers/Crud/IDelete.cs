using RekindleTaf.Model;

namespace RekindleTaf.Controllers.Crud;

public interface IDelete<T, U> where T : IValueObject
{
    U Delete(T param);
    void DeleteSuccessfully(T param);
}