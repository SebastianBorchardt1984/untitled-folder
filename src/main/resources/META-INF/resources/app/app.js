document.addEventListener('DOMContentLoaded', function() {
    const content = document.getElementById('content');
    const searchInput = document.getElementById('search');
    const rowCountSelect = document.getElementById('rowCount');
    let processInstances = [];
    let displayedCount = 0;

    function renderTable(data) {
        content.innerHTML = ''; // Clear existing content
        const table = document.createElement('table');
        const thead = document.createElement('thead');
        const tbody = document.createElement('tbody');

        // Create table header
        thead.innerHTML = `
            <tr>
                <th>ID</th>
                <th>Process Name</th>
                <th>Service URL</th>
                <th>State</th>
                <th>Details</th>
            </tr>
        `;
        table.appendChild(thead);

        // Create table rows
        data.forEach(instance => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${instance.id}</td>
                <td>${instance.processName}</td>
                <td><a href="${instance.serviceUrl}" target="_blank">${instance.serviceUrl}</a></td>
                <td>${instance.state}</td>
                <td><button onclick="window.location.href='/details.html?id=${instance.id}'">Anzeigen</button></td>
            `;
            tbody.appendChild(row);
        });

        table.appendChild(tbody);
        content.appendChild(table);
    }

    function fetchData() {
        fetch('/graphql', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            body: JSON.stringify({ query: '{ ProcessInstances { id processName serviceUrl state } }' })
        })
        .then(response => response.json())
        .then(data => {
            processInstances = data.data.ProcessInstances;
            displayedCount = parseInt(rowCountSelect.value) || 100;
            renderTable(processInstances.slice(0, displayedCount)); // Display initial rows

            // Add search functionality
            searchInput.addEventListener('input', function() {
                const searchTerm = this.value.toLowerCase().replace(/\*/g, '.*');
                const filteredData = processInstances.filter(instance => {
                    return Object.values(instance).some(value => {
                        const text = String(value).toLowerCase();
                        return text.includes(searchTerm) || text.match(new RegExp(searchTerm));
                    });
                });
                const rowCount = rowCountSelect.value === 'all' ? filteredData.length : parseInt(rowCountSelect.value);
                renderTable(filteredData.slice(0, rowCount));
            });

            // Add row count selection functionality
            rowCountSelect.addEventListener('change', function() {
                displayedCount = this.value === 'all' ? processInstances.length : parseInt(this.value);
                renderTable(processInstances.slice(0, displayedCount));
            });
        })
        .catch(error => console.error('Error fetching data:', error));
    }

    fetchData();
});