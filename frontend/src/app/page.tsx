import VendaForm from '@/components/VendaForm';
import VendaList from '@/components/VendaList';

export default function Home() {
  return (
    <div className="min-h-screen bg-gray-100 py-12">
      <div className="max-w-6xl mx-auto px-4">
        <h1 className="text-3xl font-bold text-gray-900 mb-8 text-center">
          Sistema de Vendas
        </h1>
        
        <div className="grid md:grid-cols-2 gap-8">
          <div>
            <h2 className="text-xl font-semibold text-gray-700 mb-4">
              Nova Venda
            </h2>
            <VendaForm />
          </div>
          
          <div>
            <h2 className="text-xl font-semibold text-gray-700 mb-4">
              Lista de Vendas
            </h2>
            <VendaList />
          </div>
        </div>
      </div>
    </div>
  );
}