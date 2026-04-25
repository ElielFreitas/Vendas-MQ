'use client';

import { useState } from "react";
import { vendaService } from "@/services/api";

export default function VendaForm(){
    const [produto, setProduto] = useState('');
    const [valor, setValor] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e: React.FormEvent)=>{
        e.preventDefault();
        setLoading(true);

        try{
            await vendaService.criarVenda({
                produto,
                valor: Number(valor),
            });
            setProduto('');
            setValor('');
            window.dispatchEvent(new Event('venda-criada'));
        } catch (error){
            console.error('Erro ao criar venda:', error);
        } finally{
            setLoading(false);
        }
    }; 

    return (
        <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow-md space-y-4">
            <div>
                <label className="block text-sm font-medium text-gray-700">Produto</label>
                <input
                    type="text"
                    value={produto}
                    onChange={(e) => setProduto(e.target.value)}
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                    required
                />
            </div>
            <div>
                <label className="block text-sm font-medium text-gray-700">Valor</label>
                <input
                    type="number"
                    value={valor}
                    onChange={(e) => setValor(e.target.value)}
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                    required
                />
            </div>
            <button
                type="submit"
                disabled={loading}
                className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 disabled:bg-gray-400"
            >
                {loading ? 'Enviando...' : 'Cadastrar'}
            </button>
        </form>
    );
}