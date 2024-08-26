using RekindleTaf.Model;

namespace RekindleTaf.Controllers.Crud;

public interface IGet<T, U> where T : IValueObject
{
    U Get(string param = "");
    T GetSuccessfully(string param = "");
}