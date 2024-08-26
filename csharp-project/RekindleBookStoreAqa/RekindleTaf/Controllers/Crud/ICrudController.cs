using RekindleTaf.Model;

namespace RekindleTaf.Controllers.Crud;

public interface ICrudController<T, U>
    : IGet<T, U>, IGetAll<T, U>, IPost<T, U>, IPut<T, U>, IDelete<T, U> where T : IValueObject
{
}