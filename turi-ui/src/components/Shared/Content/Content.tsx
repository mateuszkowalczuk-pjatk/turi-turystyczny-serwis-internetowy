import { ReactNode } from 'react'
import styles from './Content.module.css'

interface Props {
    title: ReactNode
    firstPanel: ReactNode
    secondPanel?: ReactNode
    thirdPanel?: ReactNode
    fourthPanel?: ReactNode
    fifthPanel?: ReactNode
}

const Content = ({ title, firstPanel, secondPanel, thirdPanel, fourthPanel, fifthPanel }: Props) => {
    return (
        <div className={styles.content}>
            {title}
            {firstPanel}
            {secondPanel}
            {thirdPanel}
            {fourthPanel}
            {fifthPanel}
        </div>
    )
}

export default Content
