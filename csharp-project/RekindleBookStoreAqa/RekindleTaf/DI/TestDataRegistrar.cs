using NUnit.Extension.DependencyInjection.Abstractions;
using RekindleTaf.Model.Factory;
using Unity;
using Unity.Lifetime;

namespace RekindleTaf.DI;

public class TestDataRegistrar : RegistrarBase<IUnityContainer>
{
    protected override void RegisterInternal(IUnityContainer container)
    {
        container.RegisterType<IFactoryKit, ValueObjectFactoryKit>(new SingletonLifetimeManager());
    }
}