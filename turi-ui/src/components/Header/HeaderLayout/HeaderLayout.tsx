import { ReactNode } from 'react'
import HeaderContent from '../HeaderContent'
import styles from './HeaderLayout.module.css'

interface Props {
    links?: ReactNode;
    buttons?: ReactNode;
}

const HeaderLayout = ({ links, buttons }: Props) => {
    return (
        <div className={styles.header}>
            <HeaderContent
                links={links}
                buttons={buttons}
            />
        </div>
    )
}

export default HeaderLayout