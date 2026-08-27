import React from 'react';
import { 
  FaPython, 
  FaJs, 
  FaReact, 
  FaNodeJs, 
  FaHtml5, 
  FaCss3Alt, 
  FaJava, 
  FaDocker, 
  FaGitAlt, 
  FaRust, 
  FaPhp, 
  FaSwift 
} from 'react-icons/fa';
import { 
  SiTypescript, 
  SiCplusplus, 
  SiGo, 
  SiKotlin, 
  SiPostgresql, 
  SiMongodb, 
  SiTailwindcss, 
  SiRubyonrails 
} from 'react-icons/si';

const techStack = [
  { name: 'JavaScript', icon: FaJs, color: '#F7DF1E' },
  { name: 'TypeScript', icon: SiTypescript, color: '#3178C6' },
  { name: 'Python', icon: FaPython, color: '#3776AB' },
  { name: 'React', icon: FaReact, color: '#61DAFB' },
  { name: 'Node.js', icon: FaNodeJs, color: '#339933' },
  { name: 'HTML5', icon: FaHtml5, color: '#E34F26' },
  { name: 'CSS3', icon: FaCss3Alt, color: '#1572B6' },
  { name: 'C++', icon: SiCplusplus, color: '#00599C' },
  { name: 'Java', icon: FaJava, color: '#5382A1' },
  { name: 'Go', icon: SiGo, color: '#00ADD8' },
  { name: 'Rust', icon: FaRust, color: '#000000' },
  { name: 'PHP', icon: FaPhp, color: '#777BB4' },
  { name: 'Swift', icon: FaSwift, color: '#F05138' },
  { name: 'Kotlin', icon: SiKotlin, color: '#7F52FF' },
  { name: 'Ruby on Rails', icon: SiRubyonrails, color: '#CC0000' },
  { name: 'Tailwind CSS', icon: SiTailwindcss, color: '#06B6D4' },
  { name: 'PostgreSQL', icon: SiPostgresql, color: '#4169E1' },
  { name: 'MongoDB', icon: SiMongodb, color: '#47A248' },
  { name: 'Docker', icon: FaDocker, color: '#2496ED' },
  { name: 'Git', icon: FaGitAlt, color: '#F05032' },
];

export default function IconGrid() {
  return (
    <div style={{ padding: '2rem', fontFamily: 'sans-serif' }}>
      <h2 style={{ textAlign: 'center', marginBottom: '1.5rem' }}>Programming Languages & Tools</h2>
      <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fill, minmax(120px, 1fr))',
        gap: '1.5rem',
        justifyItems: 'center'
      }}>
        {techStack.map((item, index) => {
          const IconComponent = item.icon;
          return (
            <div 
              key={index} 
              style={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                padding: '1rem',
                border: '1px solid #e2e8f0',
                borderRadius: '8px',
                width: '100%',
                boxSizing: 'border-box'
              }}
            >
              <IconComponent size={40} color={item.color} />
              <span style={{ marginTop: '0.5rem', fontSize: '0.875rem', fontWeight: 500 }}>
                {item.name}
              </span>
            </div>
          );
        })}
      </div>
    </div>
  );
}