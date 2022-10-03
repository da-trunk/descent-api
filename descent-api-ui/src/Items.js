// // import DataTable from 'react-data-table-component';
// import {
//     useQuery,
//     useQueryClient,
// } from "react-query";

// window.React2 = require('react');
// console.log(window.React1 === window.React2)


// const columns = [
//     {
//         name: 'Title',
//         selector: row => row.title,
//     },
//     {
//         name: 'Year',
//         selector: row => row.year,
//     },
// ];

// const mockdata = [
//     {
//         id: 1,
//         title: 'Beetlejuice',
//         year: '1988',
//     },
//     {
//         id: 2,
//         title: 'Ghostbusters',
//         year: '1984',
//     },
// ]


// function Items() {
//     const queryClient = useQueryClient()
//     const query = useQuery(['items'], useItems)
//     const { status, data, error, isFetching } = useItems();
//     console.log(data)

//     return (<b>HELLO</b>
//     );
// }; 

// /* <DataTable
// columns={columns}
// data={mockdata}
// /> */

// export default Items;