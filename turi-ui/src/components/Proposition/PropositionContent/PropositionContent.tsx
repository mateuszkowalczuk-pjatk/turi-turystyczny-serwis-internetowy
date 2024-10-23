import { ReactNode } from 'react'
import styles from './PropositionContent.module.css'

interface Props {
    title: ReactNode
    panels: ReactNode
}

const PropositionContent = ({ title, panels }: Props) => {
    return (
        <div className={styles.content}>
            {title}
            {panels}
        </div>
    )
}

export default PropositionContent
