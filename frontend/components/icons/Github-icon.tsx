import React from 'react';
import { 
  FaGithub, 
  FaCode, 
  FaCodeBranch, 
  FaTerminal, 
  FaFolderOpen, 
  FaBug,
  FaLaptopCode 
} from 'react-icons/fa';
import { SiGithubactions, SiGithubcopilot } from 'react-icons/si';

const codeIcons = [
  { name: 'GitHub', icon: FaGithub, color: '#181717' },
]

export default function GitHubIconGrid() {
  return (
    <div style={{ padding: '2rem', fontFamily: 'sans-serif' }}>
      <h3 style={{ textAlign: 'center', marginBottom: '1.5rem' }}>GitHub & Development Icons</h3>
      <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fill, minmax(130px, 1fr))',
        gap: '1rem',
        justifyItems: 'center'
      }}>
        {codeIcons.map((item, index) => {
          const Icon = item.icon;
          return (
            <div 
              key={index}
              style={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                padding: '1rem',
                border: '1px solid #e1e4e8',
                borderRadius: '6px',
                width: '100%',
                boxSizing: 'border-box'
              }}
            >
              <Icon size={36} color={item.color} />
              <span style={{ marginTop: '0.5rem', fontSize: '0.85rem' }}>{item.name}</span>
            </div>
          );
        })}
      </div>
    </div>
  );
}