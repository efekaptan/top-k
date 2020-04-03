import React from 'react';
import { Table, Badge } from 'react-bootstrap';
import { TABLE_SIZE } from '../config';

export default function (props) {
    return (
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>Frequencies of the Terms in the last 10 minutes</th>
                </tr>
            </thead>
            <tbody>
                {props.data.slice(0, TABLE_SIZE).map(item =>
                    <tr key={item.term}>
                        <td>{item.term} <Badge variant="secondary">{item.count}</Badge></td>
                    </tr>
                )}
            </tbody>
        </Table>
    )
}