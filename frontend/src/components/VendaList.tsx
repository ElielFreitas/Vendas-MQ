'use client';

import { useEffect, useState } from 'react';
import { vendaService } from '@/services/api';
import { Venda } from '@/types';

export default function VendaList() {
  const [vendas, setVendas] = useState<Venda[]>([]);
  const [loading, setLoading] = useState(true);

  const buscarVendas = async () => {
    try {
      const data = await vendaService.listarVendas();
      setVendas(data);
    } catch (error) {
      console.error('Erro ao buscar vendas:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    buscarVendas();

    const interval = setInterval(buscarVendas, 2000);

    const handler = () => buscarVendas();
    window.addEventListener('venda-criada', handler);

    return () => {
      clearInterval(interval);
      window.removeEventListener('venda-criada', handler);
    };
  }, []);

  if (loading) {
    return <div className="text-center py-8">Carregando...</div>;
  }

  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      <table className="min-w-full divide-y divide-gray-200">
        <thead className="bg-gray-50">
          <tr>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Produto</th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Valor</th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-gray-200">
          {vendas.map((venda) => (
            <tr key={venda.id}>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{venda.id}</td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{venda.produto}</td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">R$ {venda.valor.toFixed(2)}</td>
              <td className="px-6 py-4 whitespace-nowrap">
                <span className={`px-2 py-1 text-xs font-semibold rounded-full ${
                  venda.status === 'PROCESSADO' 
                    ? 'bg-green-100 text-green-800' 
                    : 'bg-yellow-100 text-yellow-800'
                }`}>
                  {venda.status === 'PROCESSADO' ? '🟢 Concluído' : '🟡 Pendente'}
                </span>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}