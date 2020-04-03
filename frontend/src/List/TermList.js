import React from 'react';
import { Table } from 'react-bootstrap';

export default function(props) {
    return (
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>%10 sample of the stream</th>
                </tr>
            </thead>
            <tbody>
                {props.terms.map((term, index) =>
                    <tr key={index}>
                        <td>{term}</td>
                    </tr>
                )}
            </tbody>
        </Table>
    )
}