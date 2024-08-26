using RekindleTaf.Model;

namespace RekindleTaf.Controllers.Crud;

public interface IPost<T, U> where T : IValueObject
{
    U Post(T param);
    T PostSuccessfully(T param);
}