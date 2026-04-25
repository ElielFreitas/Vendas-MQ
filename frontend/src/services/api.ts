import {Venda, VendaDto} from '@/types';

const API_URL = 'http://localhost:8080/api/vendas';

export const vendaService = {
    async criarVenda(data: VendaDto): Promise<Venda>{
        const response = await fetch(API_URL,{
            method:'POST',
            headers:{
                'Content-Type':'application/json',
            },
            body: JSON.stringify(data),
        });
        
        if(!response.ok){
            throw new Error("Erro ao criar venda");
        }

        return response.json();
    },

    async listarVendas(): Promise<Venda[]>{
        const response = await fetch(API_URL, {
            method: 'GET',
        });
        
        if(!response.ok){
            throw new Error("Erro ao buscar vendas");
        }

        return response.json()
    },
};