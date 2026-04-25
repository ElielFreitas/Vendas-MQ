export interface Venda{
    id: number;
    produto: string;
    valor: number;
    status: string;
}

export interface VendaDto{
    produto: string;
    valor: number;
}