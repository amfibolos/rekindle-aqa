using RekindleTaf.Model;

namespace RekindleTaf.Controllers.Crud;

public interface IPut<T, U> where T : IValueObject
{
    U Put(T param);
    void PutSuccessfully(T param);
}