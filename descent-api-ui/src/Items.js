import DataTable from 'react-data-table-component';

const columns = [
    {
        name: 'Name',
        selector: row => row.title,
    },
    {
        name: 'Health',
        selector: row => row.year,
    },
];

function Items(data) {
    console.log(data.data.allItemsByHero)
    return (<DataTable
        columns={columns}
        data={data.data.allItemsByHero.map(row => ({ id: row.hero.id, title: row.hero.id,  year: row.hero.traits.health}))} />);
}

export default Items;