import { ReactNode } from 'react'
import styles from './Layout.module.css'

interface Props {
    header: ReactNode
    content: ReactNode
    footer: ReactNode
}

const Layout = ({ header, content, footer }: Props) => {
    return (
        <div className={styles.layout}>
            {header}
            {content}
            {footer}
        </div>
    )
}

export default Layout
