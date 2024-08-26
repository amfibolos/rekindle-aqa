using NUnit.Extension.DependencyInjection.Abstractions;
using RekindleTaf.Controllers;
using Unity;

namespace RekindleTaf.DI;

public class ControllerRegistrar : RegistrarBase<IUnityContainer>
{
    protected override void RegisterInternal(IUnityContainer container)
    {
        container.RegisterType<ICustomerController, CustomerController>();
        container.RegisterType<IBookstoreController, BookstoreController>();
        container.RegisterType<IBookstoreProductController, BookstoreProductController>();
    } 
}